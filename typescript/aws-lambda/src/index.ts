/*
 context: https://docs.aws.amazon.com/lambda/latest/dg/nodejs-prog-model-context.html
 */
export const handler = async (event: any = {}): Promise<any> => {
    console.log('Hello World!');
    return JSON.stringify(event, null, 2);
};
