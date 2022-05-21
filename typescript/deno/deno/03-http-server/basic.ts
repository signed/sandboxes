import { serve } from '../dependencies.ts';

function handler(req: Request): Response {
  console.log('got called');
  return new Response('Hello, World!');
}

function handlerjj(req: Request): Response {
  let timer: number;
  const body = new ReadableStream({
    async start(controller) {
      timer = setInterval(() => {
        controller.enqueue('Hello, World!\n');
      }, 1000);
    },
    cancel() {
      clearInterval(timer);
    },
  });
  return new Response(body.pipeThrough(new TextEncoderStream()), {
    headers: {
      'content-type': 'text/plain; charset=utf-8',
    },
  });
}

serve(handler, { port: 8080 });
