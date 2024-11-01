import { UIMatch, useMatches } from '@remix-run/react'
import { Fragment, HTMLAttributes } from 'react'

type BreadcrumbMatch = UIMatch<Record<string, unknown>, { breadcrumb: (data?: unknown) => JSX.Element }>

export const Breadcrumbs = ({ ...props }: HTMLAttributes<HTMLElement>) => {
  const matches = (useMatches() as unknown as BreadcrumbMatch[]).filter(({ handle }) => handle?.breadcrumb)

  return (
    <ol
      itemScope
      itemType="https://schema.org/BreadcrumbList"
      className="flex flex-wrap items-center gap-2.5"
      {...props}
    >
      {matches.map(({ handle, data }, i) => (
        <Fragment key={i}>
          <li className="contents" itemProp="itemListElement" itemScope itemType="https://schema.org/ListItem">
            {i > 0 && <span className="text-sm">/</span>}
            {handle.breadcrumb(data)}
            <meta itemProp="position" content={`${i + 1}`} />
          </li>
        </Fragment>
      ))}
    </ol>
  )
}
