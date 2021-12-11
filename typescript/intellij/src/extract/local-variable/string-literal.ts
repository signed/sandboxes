type Options = {
  state: PermissionState
  args: string[]
}
const objectParameterFunction = (_options: Options) => {}

objectParameterFunction({args:[], state: 'prompt'})

const plainParameterFunction = (_permissionState: PermissionState) => {}

plainParameterFunction('granted')
