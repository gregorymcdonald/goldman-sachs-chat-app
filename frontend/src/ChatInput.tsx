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
      <textarea
        style={{ resize: 'none' }}
        rows={4}
        value={this.state.message}
        onChange={e => this.setState({ message: e.target.value })}
        onKeyPress={
          (event: React.KeyboardEvent<HTMLTextAreaElement>) => {
            if (event.key === 'Enter') {
              event.preventDefault();
              this.props.onSubmitMessage(this.state.message);
              this.setState({ message: '' });
            }
          }
        }
      />
    )
  }
}
