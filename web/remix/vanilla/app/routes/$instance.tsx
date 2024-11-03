import { json, LoaderFunctionArgs } from '@remix-run/node'
import { Outlet, useLoaderData, useRouteLoaderData } from '@remix-run/react'
import invariant from 'tiny-invariant'

export const loader = async ({ params }: LoaderFunctionArgs) => {
  const instance = params.instance
  invariant(instance, 'Missing instance parameter')
  const organisations = ['o-11', 'o-22', 'o-33']
  return json({ instance, organisations })
}

const OrganisationSelect = () => {
  const data = useRouteLoaderData<typeof loader>('routes/$instance')
  if (data === undefined) {
    return null
  }

  return (
    <select key={'catch'}>
      {data.organisations.map((org) => (
        <option key={org}>{org}</option>
      ))}
    </select>
  )
}

export const handle = {
  navigation: <OrganisationSelect />,
}

export default function InstanceIdentifier() {
  const data = useLoaderData<typeof loader>()
  return (
    <div>
      <h1>{data.instance}</h1>
      <Outlet />
    </div>
  )
}
