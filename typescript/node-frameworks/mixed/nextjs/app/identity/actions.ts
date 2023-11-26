'use server'
import {zfd} from 'zod-form-data'
import {redirect} from 'next/navigation'
import {cookies} from 'next/headers'

const validateIdentityTokenSchema = zfd.formData({
  uuid: zfd.text()
})

// https://nextjs.org/docs/app/building-your-application/data-fetching/forms-and-mutations
export async function validateIdentityToken(_bound: string, formData: FormData) {
  const safeParse = validateIdentityTokenSchema.safeParse(formData);
  if (!safeParse.success) {
    return new Response(null, {status: 400})
  }
  const uuid = safeParse.data.uuid;
  //todo validate and store a session based on this uuid

  cookies().set('identity', uuid, {
      httpOnly: true,
      secure: true,
    }
  )
  redirect('/')
}
