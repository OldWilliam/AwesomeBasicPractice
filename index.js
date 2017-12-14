'use strict';
import React, { Component, PropTypes }from 'react';
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

  jumpActivity(){
    console.log("MODULE_NAME: ", MyNativeModule.NATIVE_MODULE_NAME);
    MyNativeModule.jumpActivity();
  }

  showToast(){
    console.log("MODULE_NAME: ", MyNativeModule.NATIVE_MODULE_NAME);
    MyNativeModule.showToast("From JS", MyNativeModule.LONG);
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
        <HexagonImageView style={{flex:1,width:'100%'}}/>
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
