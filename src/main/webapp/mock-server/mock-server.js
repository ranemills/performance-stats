"use strict";

let express = require('express');
let bodyParser = require("body-parser");
let fs = require('fs');
let app = express();

let mockResponsesDir = 'mock-server/mock-responses/api';

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

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
  let response = JSON.parse(fs.readFileSync(mockResponsesDir + '/stats/available.json'));
  res.send(response);
});

app.get('/api/stats/filters', function(req, res) {
  let response = JSON.parse(fs.readFileSync(mockResponsesDir + '/stats/filters.json'));
  res.send(response);
});

app.get('/api/stats/snapshot', function(req, res) {
  let response = JSON.parse(fs.readFileSync(mockResponsesDir + '/stats/snapshot.json'));
  res.send(response);
});

app.get('/api/performances', function(req, res) {
  let response = JSON.parse(fs.readFileSync(mockResponsesDir + '/performances.json'));
  res.send(response);
});

app.get('/api/milestones', function(req, res) {
  let response = JSON.parse(fs.readFileSync(mockResponsesDir + '/milestones.json'));
  res.send(response);
});

app.get('/api/facets', function(req, res) {
  let response = JSON.parse(fs.readFileSync(mockResponsesDir + '/facets.json'));
  res.send(response);
});

app.post('/api/facets/new', function(req, res) {
  console.log('new facet created');
  console.log(req.body);
  res.sendStatus(200);
});

app.get('/api/facets/properties', function(req, res) {
  let response = JSON.parse(fs.readFileSync(mockResponsesDir + '/facets/properties.json'));
  res.send(response);
});

app.listen(8080, function() {
  console.log('mock server listening on port 8080');
});


