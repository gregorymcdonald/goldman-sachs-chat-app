import * as React from 'react';

interface Props {
    onSubmitMessage: (message: string) => void;
}

interface State {
    message: string
}

export default class ChatInput extends React.Component<Props, State> {
  public state = {
    message: '',
  }

  render() {
    return (
      <form
        action="."
        onSubmit={e => {
          e.preventDefault()
          this.props.onSubmitMessage(this.state.message)
          this.setState({ message: '' })
        }}
      >
        <input
          type="text"
          placeholder={'Enter message...'}
          value={this.state.message}
          onChange={e => this.setState({ message: e.target.value })}
        />
        <input type="submit" value={'Send'} />
      </form>
    )
  }
}
