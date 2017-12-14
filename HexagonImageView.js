import React from 'react'
import {requireNativeComponent, View} from 'react-native'


let iface = {
	//组件接口的名字，最好明显，方便调试
	name:'HexagonImageView',
	//必须声明，对应native view的属性
	propTypes:{
//		srcUrl:React.PropTypes.string,
		...View.propTypes//包含默认的View属性
	}
}
//第一个参数：原生视图的名字
//第二个参数：描述组件接口的对象
var HexagonImageView = requireNativeComponent('RCTHexagonImageView', iface);

export default HexagonImageView;