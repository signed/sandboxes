import { Outlet, useLoaderData } from '@remix-run/react'
import { json, LoaderFunctionArgs } from '@remix-run/node'
import { ensureSessionDataOrRedirect } from '../../sessions.server'

export async function loader({ request }: LoaderFunctionArgs) {
  const sessionData = await ensureSessionDataOrRedirect(request)

  const data = { uuid: sessionData.uuid }

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
