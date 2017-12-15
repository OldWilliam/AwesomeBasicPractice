import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {requireNativeComponent, ViewPropTypes} from 'react-native';


var iface = {
	//组件接口的名字，最好明显，方便调试
	//name:'HexagonImageView',
	//必须声明，对应native view的属性
	propTypes:{
		srcUrl:PropTypes.string,
		...ViewPropTypes,//包含默认的View属性
	}
}
//第一个参数：原生视图的名字
//第二个参数：描述组件接口的对象
var HexagonView = requireNativeComponent('RCTHexagonImageView', iface);

class HexagonImageView extends Component{
	static propTypes = {
		srcUrl:PropTypes.string,
		    ...ViewPropTypes, // 包含默认的View的属性
	}

	render(){
		const {style, srcUrl} = this.props;

		return(<HexagonView style={style} srcUrl={srcUrl}/>);
	}
}

module.exports = HexagonImageView;