var app = angular.module('chatApp', ['ngMaterial']);
app.controller('chatController', function ($scope) {
    $scope.messages = [
        {
            'sender': 'USER',
            'text': 'Hello'
		},
        {
            'sender': 'BOT',
            'text': 'Hi!!How are u?'
		},
        {
            'sender': 'USER',
            'text': 'I am fine .Thank u.'
		}
	];

});