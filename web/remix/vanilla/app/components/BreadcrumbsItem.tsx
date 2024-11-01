import { Link } from '@remix-run/react'
import { HTMLAttributes } from 'react'

export const BreadcrumbsItem = ({ children, ...props }: HTMLAttributes<HTMLElement>) => {
  return (
    <Link to={props.href} itemProp="item" {...props}>
      <span itemProp="name">{children}</span>
    </Link>
  )
}
