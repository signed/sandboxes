// https://dev.to/tejaskaneriya/when-to-use-these-nodejs-frameworks-express-koa-nest-socket-io-meteor-js-3p63
// https://www.kindacode.com/article/best-node-js-frameworks-to-build-backend-apis/
// https://npmtrends.com/express-vs-fastify-vs-hapi-vs-koa-vs-restify
import {Framework, github, compare} from "./compare.js";

const frameworks: Framework [] = [
  github('express', 'https://github.com/expressjs/express'),
  github('fastify', 'https://github.com/fastify/fastify'),
  github('loopback', 'https://github.com/loopbackio/loopback-next'),
  github('nest.js', 'https://github.com/nestjs/nest'),
  github('koa', 'https://github.com/koajs/koa'),
  github('tinyhttp', 'https://github.com/tinyhttp/tinyhttp'),
  github('restify', 'https://github.com/restify/node-restify'),
  github('next.js', 'https://github.com/vercel/next.js'),
  github('remix', 'https://github.com/remix-run/remix'),
  github('hapi', 'https://github.com/hapijs/hapi'),
  github('socket.io', 'https://github.com/socketio/socket.io'),
  github('meteor.js', 'https://github.com/meteor/meteor'),
  github('adonisjs', 'https://github.com/adonisjs/core'),
]

await compare(frameworks)
