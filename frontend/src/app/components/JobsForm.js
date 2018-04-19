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
      time: null
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
          console.log(res.data.message);
        } else {
          this.props.history.push('/jobs')
        }
      });
  }

  render() {
    return (
      <div>
        <TextField
          floatingLabelText='Notification text'
          value={this.state.text}
          onChange={this.handleTextChange}
        />
        <br/>
        <DatePicker
          hintText="Date"
          style={{display : 'inline-block'}}
          value={this.state.date}
          onChange={this.handleDateChange}
        />
        <TimePicker
          hintText="Time"
          autoOk={true}
          format="24hr"
          style={{display : 'inline-block'}}
          value={this.state.time}
          onChange={this.handleTimeChange}
        />
        <br/>
        <RaisedButton label='Submit' secondary={true} onClick={this.handleSubmit}/>
      </div>
    )
  }
}

export default JobsForm;