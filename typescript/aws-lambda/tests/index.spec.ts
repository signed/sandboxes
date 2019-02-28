import { Action, Body, Encoding, EventType, Event, handler, HttpMethod, Protocol, Region, Request, SslProtocol, Context } from "../src";

describe('lambda', () => {
  it('then', () => {
    let callback = jest.fn();

    const body: Body = {
      "action": Action.ReadOnly,
      "data": "eyJ1c2VybmFtZSI6IkxhbWJkYUBFZGdlIiwiY29tbWVudCI6IlRoaXMgaXMgcmVxdWVzdCBib2R5In0=",
      "encoding": Encoding.Base64,
      "inputTruncated": false
    };

    const request: Request = {
      body: body,
      "clientIp": "2001:0db8:85a3:0:0:8a2e:0370:7334",
      "querystring": "size=large",
      "uri": "/picture.jpg",
      "method": HttpMethod.GET,
      "headers": {
        "host": [
          {
            "key": "Host",
            "value": "d111111abcdef8.cloudfront.net"
          }
        ],
        "user-agent": [
          {
            "key": "User-Agent",
            "value": "curl/7.51.0"
          }
        ]
      },
      "origin": {
        "custom": {
          "customHeaders": {
            "my-origin-custom-header": [
              {
                "key": "My-Origin-Custom-Header",
                "value": "Test"
              }
            ]
          },
          "domainName": "example.com",
          "keepaliveTimeout": 5,
          "path": "/custom_path",
          "port": 443,
          "protocol": Protocol.Https,
          "readTimeout": 5,
          "sslProtocols": [
            SslProtocol.TLSv1, SslProtocol.TLSv1_1
          ]
        },
        "s3": {
          "authMethod": "origin-access-identity",
          "customHeaders": {
            "my-origin-custom-header": [
              {
                "key": "My-Origin-Custom-Header",
                "value": "Test"
              }
            ]
          },
          "domainName": "my-bucket.s3.amazonaws.com",
          "path": "/s3_path",
          "region": Region.US_East_1
        }
      }
    };
    const event:Event = {
      "Records": [
        {
          "cf": {
            "config": {
              "distributionDomainName": "d123.cloudfront.net",
              "distributionId": "EDFDVBD6EXAMPLE",
              "eventType": EventType.ViewerRequest,
              "requestId": "MRVMF7KydIvxMWfJIglgwHQwZsbG2IhRJ07sn9AkKUFSHS9EXAMPLE=="
            },
            "request": request
          }
        }
      ]
    };

    const context:Context = {
      getRemainingTimeInMillis: () => 43,
      functionName: 'bogus'
    };

    handler(event, context, callback);
    let data = (callback).mock.calls[0][1];
    expect(data).toBe(request);
  });
});
