import { useSubmit } from '@remix-run/react'
import { useEffect } from 'react'
import { ActionFunctionArgs, redirect } from '@remix-run/node'
import { sessionCookie } from '../cookies.server'

export const action = async (args: ActionFunctionArgs) => {
  const { request } = args
  const data = await request.formData()
  // todo validate the passed uuid
  const uuid = data.get('uuid')

  return redirect('/', {
    headers: {
      'Set-Cookie': await sessionCookie.serialize(uuid),
    },
  })
}

export default function Identity() {
  const submit = useSubmit()
  useEffect(() => {
    const uuid = globalThis.crypto.randomUUID()
    submit(
      { uuid, static: 'value' },
      {
        method: 'post',
        encType: 'application/x-www-form-urlencoded',
      },
    )
  }, [])
  return (
    <div>
      <h1>Execute Client Side script to load identity token</h1>
    </div>
  )
}
