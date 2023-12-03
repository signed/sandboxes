import { Outlet, useLoaderData } from '@remix-run/react'
import { json, LoaderFunctionArgs, redirect } from '@remix-run/node'
import { getSession } from '../../sessions.server'

export async function loader({ request }: LoaderFunctionArgs) {
  const session = await getSession(request.headers.get('Cookie'))
  const uuid = session.get('uuid')
  if (uuid === undefined) {
    return redirect('/identity')
  }
  const data = { uuid: uuid }

  return json(data)
}

export default function Home() {
  const { uuid } = useLoaderData<typeof loader>()
  return (
    <>
      <p>secure/route.tsx</p>
      <p>{uuid}</p>
      <Outlet />
    </>
  )
}
