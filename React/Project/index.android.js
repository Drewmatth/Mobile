/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import Button from 'react-native-button';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    Navigator
} from 'react-native';
var StartPage = require('./StartPage');
var Login = require('./Login');
var Register = require('./Register');
var PostLogin=require('./PostLogin');
var AddPerson=require('./AddPerson');
var AppInfo=require('./AppInfo');
export default class Project extends Component {
    render() {
        return (
            <Navigator
                initialRoute={{id: 'StartPage', name: 'Index'}}
                renderScene={this.renderScene.bind(this)}
                configureScene={(route) => {
                    if (route.sceneConfig) {
                        return route.sceneConfig;
                    }
                    return Navigator.SceneConfigs.FloatFromRight;
                }} />
        );
    }
    renderScene(route, navigator) {
        var routeId = route.id;
        switch(routeId){
            case 'StartPage':
                return (
                    <StartPage
                        navigator={navigator} />
                );
                break;
            case 'Login':
                return (
                    <Login
                        navigator={navigator} />
                );
                break;
            case 'Register':
                return (
                    <Register
                        navigator={navigator} />
                );
                break;
            case 'AppInfo':
                return (
                    <AppInfo
                        navigator={navigator} />
                );
                break;
            case 'PostLogin':
                return (
                    <PostLogin
                        navigator={navigator} userID={route.passProps.id} username={route.passProps.username} firstname={route.passProps.firstname} lastname={route.passProps.lastname}/>
                );
                break;
            case 'AddPerson':
                return (
                    <AddPerson
                        navigator={navigator}/>
                );
                break;

            default:
                return this.noRoute(navigator);
        }
    }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('Project', () => Project);
