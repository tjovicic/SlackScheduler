import React from 'react';
import TextField from 'material-ui/TextField';
import DatePicker from 'material-ui/DatePicker';
import TimePicker from 'material-ui/TimePicker';
import RaisedButton from 'material-ui/RaisedButton';
import axios from 'axios';
import moment from 'moment';

class JobsForm extends React.Component {

  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleTextChange = this.handleTextChange.bind(this);
    this.handleDateChange = this.handleDateChange.bind(this);
    this.handleTimeChange = this.handleTimeChange.bind(this);

    this.state = {
      text: '',
      date: null,
      time: null,
      error: null
    }
  }

  handleTextChange(event, text) {
    this.setState({text: text})
  };

  handleDateChange(event, date) {
    this.setState({date: date})
  };

  handleTimeChange(event, time) {
    this.setState({time: time})
  };

  handleSubmit(event) {
    event.preventDefault();
    const date = moment(this.state.date).format('YYYY-MM-DD');
    const time = moment(this.state.time).format('HH:mm:ss');

    axios.post('/api/jobs', {text: this.state.text, status: 'PENDING', time: date+'T'+time})
      .then(res => {
        if (res.data.status === 'fail') {
          this.setState({error: res.data.message});
        } else {
          this.props.history.push('/table')
        }
      });
  }

  render() {
    return (
      <div>
        <TextField
          floatingLabelText='Text'
          value={this.state.text}
          onChange={this.handleTextChange}
        />
        <br/>
        <DatePicker
          hintText="Date"
          value={this.state.date}
          onChange={this.handleDateChange}
          errorText={this.state.error}
        />
        <TimePicker
          hintText="Time"
          autoOk={true}
          format="24hr"
          value={this.state.time}
          onChange={this.handleTimeChange}
          errorText={this.state.error}
        />
        <br/>
        <RaisedButton label='Submit' secondary={true} onClick={this.handleSubmit}/>
      </div>
    )
  }
}

export default JobsForm;