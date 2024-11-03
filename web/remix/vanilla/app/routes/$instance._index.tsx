import { json, LoaderFunctionArgs } from '@remix-run/node'
import invariant from 'tiny-invariant'
import { useLoaderData } from '@remix-run/react'

export const loader = async ({ params }: LoaderFunctionArgs) => {
  const instance = params.instance
  invariant(instance, 'Missing instance parameter')
  const organisations = ['o-1', 'o-2', 'o-3']
  return json({ instance, organisations })
}

export default function InstanceIndex() {
  const data = useLoaderData<typeof loader>()
  return (
    <ul>
      {data.organisations.map((o) => (
        <li key={o}> {o}</li>
      ))}
    </ul>
  )
}
