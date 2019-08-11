package me.jim.wx.annotationcompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Date: 2019/8/9
 * Name: wx
 * Description:
 */
public class Layout {

    private final String className;
    private final StringBuilder statement;
    private String objectName;
    private List<Layout> children = new ArrayList<>();


    private Layout(Builder builder) {

        this.className = builder.className;

        Layout parent = builder.parent;
        if (parent != null) {
            parent.addChildren(this);
        }

        String[] classFullName = className.split("\\.");
        if (classFullName.length == 0) {
            objectName = "obj_".concat(className);
        } else {
            if (classFullName.length > 1) {
                int index = classFullName.length - 1;
                objectName = "obj_".concat(classFullName[index]);
            }
        }

        HashMap<String, String> attr = builder.attr;
        boolean hasId = false;
        if (attr.get("id") != null) {
            String id = attr.get("id");
            objectName = id.split("/")[1];
            hasId = true;
        }

        if (parent == null) {
            objectName = "root";
            statement = new StringBuilder();
        } else {
            statement = parent.statement;
        }

        statement.append(String.format(Locale.US, "\n%s %s = new %s(context);", className, objectName, className)).append("\n");
        if (hasId) {
            statement.append(String.format(Locale.US, "%s.setId(R.id.%s);", objectName, objectName)).append("\n");
        }

        String text = attr.get("text");
        if (text != null) {
            statement.append(String.format(Locale.US, "%s.setText(\"%s\");", objectName, text)).append("\n");
        }

        if (parent != null) {
            statement.append(String.format(Locale.US, "%s.addView(%s);", parent.getObjectName(), objectName)).append("\n");
        }
    }

    private String getObjectName() {
        return objectName;
    }

    public String getStatement() {
        return statement.toString();
    }

    public void addChildren(Layout child) {
        children.add(child);
    }

    public static class Builder {
        private final String className;
        private HashMap<String, String> attr = new HashMap<>();
        private Layout parent;

        public Builder(String name) {
            this.className = BindingSets.get(name);
        }

        public Builder buildAttr(String name, String value) {
            attr.put(name, value);
            return this;
        }

        public Layout build() {
            Layout layout = new Layout(this);
            return layout;
        }

        public Builder parent(Layout parent) {
            this.parent = parent;
            return this;
        }
    }

    @Override
    public String toString() {
        return toString("");
    }

    private String toString(String indent) {
        return className + "  child: " + children.size() + "\n" + childToString(indent);
    }

    private String childToString(String indent) {
        indent += "    ";
        StringBuilder sb = new StringBuilder();
        if (children.size() > 0) {
            for (Layout child : children) {
                sb.append(indent);
                sb.append(child.toString(indent));
            }
        }
        return sb.toString();
    }
}
