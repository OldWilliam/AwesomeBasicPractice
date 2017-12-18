'use strict';
import React, { Component, PropTypes }from 'react';
import {DeviceEventEmitter} from 'react-native'
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableOpacity
} from 'react-native';

import MyNativeModule from './MyNativeModule';
import HexagonImageView from './HexagonImageView';

class HelloWorld extends React.Component {

  componentWillMount(){
    console.log("componentWillMount");
    //接收native的事件
    DeviceEventEmitter.addListener(MyNativeModule.TestEvent, info => {
      console.log(info);
    })
  }
  //js跳转Activity
  jumpActivity(){
    console.log("MODULE_NAME: ", MyNativeModule.NATIVE_MODULE_NAME);
    MyNativeModule.jumpActivity();
  }

  //显示Toast
  showToast(){
    console.log("MODULE_NAME: ", MyNativeModule.NATIVE_MODULE_NAME);
    MyNativeModule.showToast("From JS", MyNativeModule.LONG);
  }

  //测试回调
  testCallBack(){
    MyNativeModule.testCallBack(19,1,(result) => {
      console.log(MyNativeModule.NATIVE_MODULE_NAME, result);
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity onPress={this.jumpActivity}>
          <Text style={styles.hello}>StartActivity</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.showToast}>
          <Text style={styles.hello}>ShowToast</Text>
        </TouchableOpacity>
        <HexagonImageView style={{flex:1,width:'100%'}} srcUrl = 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=367612666,3307031859&fm=27&gp=0.jpg'/>
        <TouchableOpacity onPress={this.testCallBack}>
          <Text style={styles.hello}>testCallBack</Text>
        </TouchableOpacity>
      </View>
    )
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});

AppRegistry.registerComponent('my-react-native-app', () => HelloWorld);
