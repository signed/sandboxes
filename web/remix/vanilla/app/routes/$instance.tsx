import { type ActionFunctionArgs, json, LoaderFunctionArgs, redirect } from '@remix-run/node'
import { Form, Outlet, useLoaderData, useParams, useRouteLoaderData, useSubmit } from '@remix-run/react'
import invariant from 'tiny-invariant'
import { z } from 'zod'

const OrganisationFormData = z.object({ instance: z.string(), organisation: z.string() })

export const action = async (args: ActionFunctionArgs) => {
  const instance = args.params.instance
  const formData = await args.request.formData()
  const validationResult = OrganisationFormData.safeParse({ ...Object.fromEntries(formData), instance })

  if (!validationResult.success) {
    return redirect(`/${instance}`)
  }
  const dto = validationResult.data
  return redirect(`/${dto.instance}/${dto.organisation}`)
}

export const loader = async ({ params }: LoaderFunctionArgs) => {
  const instance = params.instance
  invariant(instance, 'Missing instance parameter')
  const organisations = ['o-11', 'o-22', 'o-33']
  return json({ instance, organisations })
}

const OrganisationSelect = () => {
  const submit = useSubmit()
  const params = useParams()
  const data = useRouteLoaderData<typeof loader>('routes/$instance')
  const instance = params.instance
  if (data === undefined || instance === undefined) {
    return null
  }

  return (
    <Form
      method={'POST'}
      action={`/${instance}`}
      onChange={(event) => {
        submit(event.currentTarget)
      }}
    >
      <select key={'catch'} name={'organisation'} defaultValue={'no-selection'}>
        <option className={'hidden'} disabled value={'no-selection'}>
          pick
        </option>
        {data.organisations.map((org) => (
          <option key={org}>{org}</option>
        ))}
      </select>
    </Form>
  )
}

export const handle = {
  navigation: <OrganisationSelect key="OrganisationSelect" />,
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
