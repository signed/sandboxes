import { json, LoaderFunctionArgs } from '@remix-run/node'
import { useLoaderData } from '@remix-run/react'
import invariant from 'tiny-invariant'

export const loader = async ({ params }: LoaderFunctionArgs) => {
  invariant(params.instance, 'Missing instance parameter')
  invariant(params.organisation, 'Missing organisation parameter')
  return json({ instance: params.instance, organisation: params.organisation })
}

export default function InstanceIdentifier() {
  const data = useLoaderData<typeof loader>()
  return (
    <div>
      <h1>{`${data.instance}>${data.organisation}`}</h1>
    </div>
  )
}
