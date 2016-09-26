"use strict";

var express = require('express');
var fs = require('fs');
var app = express();

app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");

  // intercept OPTIONS method
  if ('OPTIONS' === req.method) {
    res.sendStatus(200);
  }
  else {
    next();
  }
});

app.get('/api/auth/user', function (req, res) {
  res.send({name: 'testName', hasImported: true});
});

app.get('/api/stats/available', function(req, res) {
  var response = JSON.parse(fs.readFileSync('mock-responses/api/stats/available.json'));
  res.send(response);
});

app.get('/api/stats/filters', function(req, res) {
  var response = JSON.parse(fs.readFileSync('mock-responses/api/stats/filters.json'));
  res.send(response);
});

app.get('/api/stats/snapshot', function(req, res) {
  var response = JSON.parse(fs.readFileSync('mock-responses/api/stats/snapshot.json'));
  res.send(response);
});

app.get('/api/performances/list', function(req, res) {
  var response = JSON.parse(fs.readFileSync('mock-responses/api/performances/list.json'));
  res.send(response);
});

app.get('/api/milestones', function(req, res) {
  var response = JSON.parse(fs.readFileSync('mock-responses/api/milestones.json'));
  res.send(response);
});

app.get('/api/facets', function(req, res) {
  var response = JSON.parse(fs.readFileSync('mock-responses/api/facets.json'));
  res.send(response);
});

app.listen(8080, function() {
  console.log('mock server listening on port 8080');
});


