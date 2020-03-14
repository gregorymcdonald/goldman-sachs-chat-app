import * as React from 'react';
import ChatInput from './ChatInput'
import ChatMessage from './ChatMessage'

import './Chat.scss';

const WebSocketUrl = 'ws://localhost:8080'

interface State {
    name: string,
    messages: any[]
}

/**
 * Class for chat component.
 */
export default class Chat extends React.Component<{}, State> {
  public state: State = {
    name: 'Bob',
    messages: []
  }

  private websocket: WebSocket = new WebSocket(WebSocketUrl);

  public componentDidMount(): void {
    this.websocket.onopen = () => {
      console.log(`Connected to WebSocket server at ${WebSocketUrl}`);
    }

    this.websocket.onmessage = evt => {
      // When a message is received, add it to the list of messages.
      const messages = JSON.parse(evt.data);
      this.setState(state => ({ messages: messages }));
    }

    this.websocket.onclose = () => {
      console.log(`Disconnected from WebSocket server at ${WebSocketUrl}`);
    }
  }

  /**
   * Submit the message entered by the user.
   * @param message the message text to submit
   */
  public submitMessage(message: string): void {
    if (!message) {
      return;
    }
    // on submitting the ChatInput form, send the message, add it to the list and reset the input
    const messageObj = { name: this.state.name, message: message }
    this.websocket.send(JSON.stringify(messageObj));
  }

  render() {
    return (
      <div className="chat-container">
        <span className="screenname">ScreenName</span>
        <input
          className="screenname"
          type="text"
          placeholder={'Enter your name...'}
          value={this.state.name}
          onChange={e => this.setState({ name: e.target.value })}
        />
        <div className="messages-container windows-bevel">
        {
          this.state.messages.map((message, index) =>
            <ChatMessage
              key={index}
              message={message.message}
              name={message.name}
              nameColor={this.state.name === message.name ? 'rgb(1, 1, 255)' : 'rgb(250, 2, 2)'}
            />,
          )
        }
        </div>
        <ChatInput onSubmitMessage={messageString => this.submitMessage(messageString)} />
      </div>
    )
  }
}