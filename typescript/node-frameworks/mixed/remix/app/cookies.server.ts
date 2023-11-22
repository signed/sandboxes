import { createCookie } from "@remix-run/node";

export const sessionCookie = createCookie('s', {
  maxAge: 604_800, // one week
})
