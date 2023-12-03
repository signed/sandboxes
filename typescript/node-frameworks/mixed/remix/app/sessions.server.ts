import { createCookie, createFileSessionStorage, redirect } from '@remix-run/node' // or cloudflare/deno

export type SessionData = {
  uuid: string
}

export type SessionFlashData = {
  error: string
}

export const ensureSessionDataOrRedirect = async (request: Request): Promise<SessionData> => {
  const session = await getSession(request.headers.get('Cookie'))
  const uuid = session.get('uuid')
  if (uuid === undefined) {
    throw redirect('/identity')
  }

  return { uuid }
}

// In this example the Cookie is created separately.
const sessionCookie = createCookie('__session', {
  secrets: ['r3m1xr0ck5'],
  sameSite: true,
})

const { getSession, commitSession, destroySession } = createFileSessionStorage<SessionData, SessionFlashData>({
  dir: './var',
  cookie: sessionCookie,
})
export { getSession, commitSession, destroySession }
