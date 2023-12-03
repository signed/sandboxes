import { useLoaderData, useSubmit } from '@remix-run/react'
import { useEffect } from 'react'
import { ActionFunctionArgs, json, LoaderFunctionArgs, redirect } from '@remix-run/node'
import { manualSessionCookie } from '../cookies.server'
import { getSession, commitSession } from '../sessions.server'

export async function loader({ request }: LoaderFunctionArgs) {
  const session = await getSession(request.headers.get('Cookie'))

  if (session.has('uuid')) {
    // Redirect to the home page if they are already signed in.
    return redirect('/')
  }

  const data = { error: session.get('error') }

  return json(data, {
    headers: {
      'Set-Cookie': await commitSession(session),
    },
  })
}

export const action = async (args: ActionFunctionArgs) => {
  const { request } = args
  const session = await getSession(request.headers.get('Cookie'))
  const data = await request.formData()
  // todo validate the passed uuid
  const uuid = data.get('uuid')

  if (uuid === null || typeof uuid === 'object') {
    session.flash('error', 'No UUID provided')
    // Redirect back to the login page with errors.
    return redirect('/identity', {
      headers: {
        'Set-Cookie': await commitSession(session),
      },
    })
  }

  console.log('catch me')
  session.set('uuid', uuid)

  const headers = new Headers()
  const value = await commitSession(session)
  console.log(value)
  headers.append('Set-Cookie', value)
  headers.append('Set-Cookie', await manualSessionCookie.serialize(uuid))

  return redirect('/', {
    headers,
  })
}

export default function Identity() {
  const { error } = useLoaderData<typeof loader>()
  const submit = useSubmit()
  useEffect(() => {
    if (error) {
      return
    }
    const uuid = globalThis.crypto.randomUUID()
    submit(
      { uuid, static: 'value' },
      {
        method: 'post',
        encType: 'application/x-www-form-urlencoded',
      },
    )
  }, [])
  if (error) {
    return <p>Unable to load your identity in this environment</p>
  }
  return (
    <div>
      <h1>Execute Client Side script to load identity token</h1>
    </div>
  )
}
