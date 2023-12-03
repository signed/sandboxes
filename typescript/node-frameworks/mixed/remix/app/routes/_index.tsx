import type { LoaderFunctionArgs } from '@remix-run/node'
import { redirect } from '@remix-run/node' // or cloudflare/deno

import { manualSessionCookie } from '../cookies.server'
import { useLoaderData } from '@remix-run/react'

export async function loader({ request }: LoaderFunctionArgs) {
  const cookieHeader = request.headers.get('Cookie')
  const maybeSessionCookie = await manualSessionCookie.parse(cookieHeader)
  // check that the session is valid
  if (maybeSessionCookie === null) {
    return redirect('/identity')
  }
  return { session: maybeSessionCookie }
}

export default function Home() {
  const { session } = useLoaderData<typeof loader>()
  return (
    <div>
      <h1>Welcome!</h1>
      <p>session = {session}</p>
    </div>
  )
}
