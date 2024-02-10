import { App } from '../App.tsx'
import type { Meta, StoryObj } from '@storybook/react'

const meta = {
  title: 'Flex',
  tags: ['autodocs'],
} satisfies Meta<typeof App>
export default meta

type Story = StoryObj<typeof meta>

export const Primary: Story = {
  parameters: {

  },

  render: () => {
    return (
      <>
        <div className="flex flex-row items-center">
          <div className="border-2 ">one</div>
          <div className="border-2">
            one
            <br />
            two
            <br />
            three
            <br />
          </div>
        </div>
      </>
    )
  },
}
