import { createCookie } from '@remix-run/node'

export const manualSessionCookie = createCookie('s', {
  maxAge: 604_800, // one week
})
