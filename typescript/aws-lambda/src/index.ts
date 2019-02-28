/*
 context: https://docs.aws.amazon.com/lambda/latest/dg/nodejs-prog-model-context.html
 */

export interface Event {
  Records: Record[];
}

export interface Record {
  cf: Cloudfront;
}

export interface Cloudfront {
  config: Configuration;
  request: Request;
  response?: Response;
}

export interface Configuration {
  /**
   * The domain name of the distribution that's associated with the request.
   */
  readonly distributionDomainName: string;
  /**
   * The ID of the distribution that's associated with the request.
   */
  readonly distributionId: string;
  /**
   * The type of trigger that's associated with the request.
   */
  readonly eventType: EventType;
  readonly requestId: string;
}

export enum EventType {
  ViewerRequest = 'viewer-request',
  OriginRquest = 'origin-request',
  OriginResponse = 'origin-request',
  ViewerResponse = 'viewer-response'
}

/**
 * the event object that CloudFront passes to a Lambda function when the function is
 * triggered by a CloudFront viewer request event or an origin request event:
 */
export interface Request {
  readonly body: Body;
  /**
   * The IP address of the viewer that made the request. If the viewer used
   * an HTTP proxy or a load balancer to send the request, the value
   * is the IP address of the proxy or load balancer.
   */
  clientIp: string;
  /**
   * The query string, if any, that CloudFront received in the viewer request.
   * If the viewer request doesn't include a query string, the event
   * structure still includes querystring with an empty value. For more
   * information about query strings, see Caching Content Based
   * on Query String Parameters.
   */
  querystring: string,
  /**
   * The relative path of the requested object. Note the following:
   * - The new relative path must begin with a slash (like this: /).
   * - If a function changes the URI for a request, that changes the object that the viewer is requesting.
   * - If a function changes the URI for a request, that doesn't change the cache behavior for the request or the origin that the request is forwarded to.
   */
  uri: string,
  /**
   * The HTTP method of the viewer request.
   */
  method: HttpMethod,
  headers: Headers,
  origin: Origin,
}

/**
 * You can specify either a custom origin or an Amazon S3 origin in
 * a single request, but not both.
 */
export interface Origin {
  custom: Custom;
  s3: S3;
}

export interface Custom {
  /**
   * You can include custom headers with the request by specifying a header
   * name and value pair for each custom header. You can't add headers that are
   * blacklisted for origin custom headers or hooks, and a header with the
   * ame name can't be present in request.headers or
   * in request.origin.custom.customHeaders. The restrictions
   * for request.headers also apply to custom headers. For more information,
   * see Custom Headers that CloudFront Can't Forward to Your Origin and
   * Blacklisted Headers.
   */
  customHeaders: Headers;
  /**
   * The domain name of the origin server, like www.example.com. The domain name
   * can't be empty, can't include a colon (:), and can't use the IPV4 address
   * format. The domain name can be up to 253 characters.
   */
  domainName: string,
  /**
   * How long, in seconds, that CloudFront should try to maintain the connection
   * to your origin after receiving the last packet of a response. The value must
   * be a number in the range of 1 to 60 seconds.
   */
  keepaliveTimeout: number,
  /**
   * The directory path at the server where the request should locate content.
   * The path should start with a slash (/) but should have no trailing / (like path/).
   * The path should be URL encoded, with a maximum length of 255 characters.
   */
  path: string,
  /**
   * The port at your custom origin. The port must be 80 or 443,
   * or a number in the range of 1024 to 65535.
   */
  port: number,

  /**
   * origin requests only
   *
   * The origin protocol policy that CloudFront should use when fetching objects
   * from your origin. The value can be http or https.
   */
  protocol: Protocol,
  /**
   * How long, in seconds, CloudFront should wait for a response after forwarding
   * a request to the origin, and how long CloudFront should wait after receiving
   * a packet of a response before receiving the next packet. The value must be a
   * number in the range of 4 to 60 seconds.
   */
  readTimeout: number,
  /**
   * The SSL protocols that CloudFront can use when establishing an HTTPS connection
   * with your origin. Values can be the following: TLSv1.2, TLSv1.1, TLSv1, SSLv3.
   */
  sslProtocols: SslProtocol[];
}

export interface S3 {
  /**
   *  Set to origin-access-identity if your Amazon S3 bucket has an origin access identity (OAI) set up, or none if you aren’t using OAI. If you set authMethod to origin-access-identity, there are several requirements:
   *  - You must specify a Region in your header.
   *  - You must use the same OAI when you switch from one Amazon S3 origin to another.
   *  - You can’t use an OAI when you switch from a custom origin to an Amazon S3 origin.
   *  For more information about using an OAI, see Restricting Access to Amazon S3 Content by Using an Origin Access Identity.
   */
  authMethod: string,
  /**
   * You can include custom headers with the request by specifying a header
   * name and value pair for each custom header. You can't add headers that
   * are blacklisted for origin custom headers or hooks, and a header with the
   * same name can't be present in request.headers
   * or in request.origin.custom.customHeaders. The restrictions
   * for request.headers also apply to custom headers. For more information,
   * see Custom Headers that CloudFront Can't Forward to Your Origin
   * and Blacklisted Headers.
   */
  customHeaders: Headers,
  /**
   * The domain name of the Amazon S3 origin server, like my-bucket.s3.amazonaws.com.
   * The domain name can't be empty, and must be an allowed bucket name (as defined by Amazon S3).
   * Do not use a Region-specific endpoint, like my-bucket.s3-eu-west-1.amazonaws.com.
   * The name can be up to 128 characters, and must be all lowercase.
   */
  domainName: string,
  /**
   * The directory path at the server where the request should locate content.
   * The path should start with a slash (/) but should have no trailing / (like path/).
   */
  path: string,

  /**
   * The Region for your Amazon S3 bucket. This is required only if you use OAI.
   */
  region: Region
}

export enum Region {
  US_East_1 = 'us-east-1'
}

export enum SslProtocol {
  TLSv1_3 = 'TLSv1.2',
  TLSv1_1 = 'TLSv1.1',
  TLSv1 = 'TLSv1',
  SSLv3 = 'SSLv3'
}

export enum Protocol {
  Http = 'http',
  Https = 'https'
}

export interface Headers {
  [key: string]: Header[]
}

export interface Header {
  key: string;
  value: string;
}

export enum HttpMethod {
  GET = 'GET'
}

export interface Body {
  /**
   * The action that you intend to take with the body. The options for action are the following:
   * read-only: This is the default. When returning the response from the Lambda function, if action is read-only, Lambda@Edge ignores any changes to encoding or data.
   * replace: Specify this when you want to replace the body sent to the origin.
   */
  action: Action;
  /**
   * The request body content.
   */
  data: string;
  /**
   * The encoding for the body. When Lambda@Edge exposes the body to the Lambda function, it first converts the body to base64 encoding. If you choose replace for
   * he action to replace the body, you can opt to use text or base64 (the default) encoding.
   * If you specify encoding as base64 but the body is not valid base64, CloudFront returns an error.
   */
  encoding: Encoding;
  /**
   * A Boolean flag that indicates if the body was truncated by Lambda@Edge.
   * For more information, see Size Limits for Body with the Include Body Option.
   */
  readonly inputTruncated: boolean;
}

export enum Action {
  ReadOnly = 'read-only',
  Replace = 'replace'
}

export enum Encoding {
  Base64 = 'base64',
  Text = 'text'
}

/**
 * the event object that CloudFront passes to a Lambda function when the function
 * is triggered by a CloudFront viewer response event or an origin response event:
 */
export interface Response {
  config: Configuration;
}

export interface Context {
  getRemainingTimeInMillis: () => number;
  functionName: string;
}

// event structure https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/lambda-event-structure.html

export const handler = (event: Event, context: Context, callback: (error: any, next: Request | Response) => void): void => {
  console.log('Hello World!');
  callback(null, event.Records[0].cf.request);
};
