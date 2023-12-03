import { Outlet } from '@remix-run/react'

export default function SecureIndex() {
  return (
    <>
      <p>secure._index/route.tsx</p>
      <Outlet />
    </>
  )
}
