import util from 'util';
import * as winston from 'winston';

const levels = ['trace', 'debug', 'info', 'warn', 'error', 'fatal'] as const;
type LogLevel = typeof levels[number]

// https://github.com/winstonjs/winston/tree/2.x#logging-levels
const nodeMailerLogLevelToWinstonNpmLevels = {
  'fatal': 'error',
  'error' : 'error',
  'warn': 'warn',
  'info': 'info',
  //'': 'verbose', //no match for verbose
  'debug': 'debug',
  'trace': 'silly'
}

const fileTransport = new (winston.transports.File)({
  "timestamp": true,
  "json": false,
  "filename": "log/nodemailer.log",
  "maxFiles": 5,
  "maxsize": 10485760
});
const consoleTransport = new (winston.transports.Console)({
});

const winstonLogger = new (winston.Logger)({
  level: 'silly',
  transports: [
    consoleTransport,
    fileTransport
  ]
});

const _logFunc = (logger: any, level: LogLevel, data: any, message: any, ...args: any[]) => {
  let entry: any = {};
  Object.keys(data || {}).forEach(key => {
    if (key !== 'level') {
      entry[key] = data[key];
    }
  });

  logger[level](entry, message, ...args);
};

/**
 * Copied over from https://github.com/nodemailer/nodemailer/blob/master/lib/shared/index.js#L339
 * Returns a bunyan-compatible logger interface. Uses either provided logger or
 * creates a default console logger
 *
 * @return {Object} bunyan compatible logger
 */
export const getLogger = () => {
  let response: any = {};
  let logger = createDefaultLogger(levels);

  levels.forEach(level => {
    response[level] = (data: any, message: string, ...args: any []) => {
      _logFunc(logger, level, data, message, ...args);
    };
  });

  return response;
};


/**
 * Generates a bunyan-like logger that prints to console
 *
 * @returns {Object} Bunyan logger instance
 */
export function createDefaultLogger(levels: readonly LogLevel[]) {
  let levelMaxLen = 0;
  let levelNames = new Map();
  levels.forEach((level: LogLevel) => {
    if (level.length > levelMaxLen) {
      levelMaxLen = level.length;
    }
  });

  levels.forEach(level => {
    let levelName = level.toUpperCase();
    if (levelName.length < levelMaxLen) {
      levelName += ' '.repeat(levelMaxLen - levelName.length);
    }
    levelNames.set(level, levelName);
  });

  let print = (level: LogLevel, entry: any, message: string, ...args: any[]) => {
    let prefix = '';
    if (entry) {
      if (entry.tnx === 'server') {
        prefix = 'S: ';
      } else if (entry.tnx === 'client') {
        prefix = 'C: ';
      }

      if (entry.sid) {
        prefix = '[' + entry.sid + '] ' + prefix;
      }

      if (entry.cid) {
        prefix = '[#' + entry.cid + '] ' + prefix;
      }
    }

    message = util.format(message, ...args);
    message.split(/\r?\n/).forEach(line => {
      const winstonNpmLevel = nodeMailerLogLevelToWinstonNpmLevels[level];
      winstonLogger.log(winstonNpmLevel, prefix + line);
    });
  };

  let logger: any = {};
  levels.forEach((level: LogLevel) => {
    logger[level] = print.bind(null, level);
  });

  return logger;
}
