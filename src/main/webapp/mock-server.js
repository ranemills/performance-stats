var express = require('express');
var app = express();

app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");

  // intercept OPTIONS method
  if ('OPTIONS' == req.method) {
    res.sendStatus(200);
  }
  else {
    next();
  }
});

app.get('/api/auth/user', function (req, res, next) {
  res.send({name: 'testName', hasImported: true});
  next();
});

app.listen(8080, function() {
  console.log('mock server listening on port 8080');
});
