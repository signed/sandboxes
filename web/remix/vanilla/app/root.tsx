import { Form, Links, Meta, NavLink, Outlet, Scripts, ScrollRestoration, useMatches } from '@remix-run/react'
import { type ActionFunctionArgs, redirect } from '@remix-run/node'
import { z } from 'zod'
import { FaHouse } from 'react-icons/fa6'

import './tailwind.css'

const InstanceFormData = z.object({ instance: z.string() })

export const action = async (args: ActionFunctionArgs) => {
  const formData = await args.request.formData()
  const validationResult = InstanceFormData.safeParse(Object.fromEntries(formData))

  if (!validationResult.success) {
    return redirect('/')
  }
  return redirect(`/${validationResult.data.instance}/`)
}

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>
      <body>
        {children}
        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  )
}

export default function App() {
  const matches = useMatches()
  const navigation = matches.filter((match) => match.handle?.navigation)
  return (
    <div>
      <div className={'flex gap-2 p-3'}>
        <NavLink to={'/'}>
          <FaHouse />
        </NavLink>
        <Form role="search" method={'POST'}>
          <input placeholder="Instance" type="text" name="instance" />
        </Form>
        <div>{navigation.map((m) => m.handle.navigation)}</div>
      </div>
      <Outlet />
    </div>
  )
}
