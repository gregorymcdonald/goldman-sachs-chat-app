import * as React from 'react';

interface Props {
    name: string;
    message: string;
}

export default class ChatInput extends React.Component<Props> {
    render() {
        return (
            <p>
                <strong>{this.props.name}</strong> <em>{this.props.message}</em>
            </p>
        )
    }
}