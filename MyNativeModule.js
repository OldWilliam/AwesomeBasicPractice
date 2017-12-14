//原生模块封装为Js模块
'use strict';
import {NativeModules} from 'react-native';

//这里的MyNativeModule必须对应
//public String getName（）返回的字符串
export default NativeModules.MyNativeModule;