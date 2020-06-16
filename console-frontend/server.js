// server.js
const express = require('express');
// const favicon = require('express-favicon');
const path = require('path');
const port = process.env.PORT || 8082;
const app = express();

app.use(express.static(__dirname+'/public/'));

app.get('*', (req, res) => {
  res.sendFile(path.resolve(__dirname+'/public/', 'index.html'));
});

app.listen(port);
console.log('Server started on port 8080');