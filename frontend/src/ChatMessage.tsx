import * as React from 'react';

interface Props {
    name: string;
    nameColor?: string;
    message: string;
}

export default class ChatInput extends React.Component<Props> {
    render() {
        return (
            <span className="message">
                <strong style={{ color: this.props.nameColor || 'black'}}>
                    {this.props.name}:&nbsp;
                </strong>
                <span>{this.props.message}</span>
            </span>
        )
    }
}