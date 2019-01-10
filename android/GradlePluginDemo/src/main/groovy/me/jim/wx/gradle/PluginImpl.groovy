package me.jim.wx.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * https://blog.csdn.net/sbsujjbcy/article/details/50782830
 */

class PluginImpl implements Plugin<Project> {
    void apply(Project project) {
        project.task('HelloWorld') << {
            println "Hello gradle plugin"
        }
    }
}