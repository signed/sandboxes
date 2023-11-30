// https://nextjs.org/docs/app/api-reference/file-conventions/route
// https://nextjs.org/docs/app/api-reference/functions/next-response

import { NextResponse } from 'next/server'
import { z } from 'zod'
const requestSchema = z.object({
  challenge: z.string(),
})

export const dynamic = 'force-dynamic' // defaults to force-static
export async function POST(request: Request, _context: unknown) {
  const result = requestSchema.safeParse(await request.json())
  if (!result.success) {
    // https://github.com/vercel/next.js/discussions/51475
    //return NextResponse.json(null,{
    //  status: 400
    //})
    return new Response(null, {
      status: 400,
    })
  }
  const response = result.data.challenge
    .split('+')
    .map((i) => i.trim())
    .map((i) => parseInt(i, 10))
    .reduce((sum, current) => sum + current, 0)
  return NextResponse.json({ response })
}
