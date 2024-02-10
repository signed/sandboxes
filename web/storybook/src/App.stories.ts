import type { Meta, StoryObj } from '@storybook/react'

import { App } from './App.js'

// More on how to set up stories at: https://storybook.js.org/docs/writing-stories#default-export
const meta = {
  title: 'App',
  component: App,
  parameters: {
    layout: 'centered',
  },
} satisfies Meta<typeof App>

export default meta
type Story = StoryObj<typeof meta>

export const Primary: Story = {
  args: {
    primary: true,
    label: 'Button',
  },
}
