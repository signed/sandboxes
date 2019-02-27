/*
 context: https://docs.aws.amazon.com/lambda/latest/dg/nodejs-prog-model-context.html
 */

interface Request {

}

interface Response {

}

export const handler = (event: any = {}, context: any = {}, callback: (error: any, next: Request | Response) => void): void => {
  console.log('Hello World!');
  let data = JSON.stringify(event, null, 2);
  callback(null, data);
};
