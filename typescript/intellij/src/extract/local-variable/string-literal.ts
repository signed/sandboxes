type Options = {
  state: PermissionState
  args: string[]
}

const objectParameterFunction = (_options: Options) => {}

const plainParameterFunction = (_permissionState: PermissionState) => {}

plainParameterFunction('granted')
