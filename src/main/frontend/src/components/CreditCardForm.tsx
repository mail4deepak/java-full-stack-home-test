import React, { useState } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  TextField,
  Typography,
  Alert,
} from '@mui/material';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

// Create axios instance with base URL
const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080',
});

interface CreditCardFormData {
  name: string;
  cardNumber: string;
  limit: string;
}

const CreditCardForm: React.FC = () => {
  const [formData, setFormData] = useState<CreditCardFormData>({
    name: '',
    cardNumber: '',
    limit: '',
  });
  const [error, setError] = useState<string>('');

  const queryClient = useQueryClient();

  const apiUrl = process.env.REACT_APP_API_URL;

  const addCardMutation = useMutation({
    mutationFn: (data: CreditCardFormData) =>
      api.post(`${apiUrl}/api/v1/cards`, {
        name: data.name,
        cardNumber: data.cardNumber,
        limit: parseFloat(data.limit),
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['cards'] });
      setFormData({ name: '', cardNumber: '', limit: '' });
      setError('');
    },
    onError: (error: any) => {
      if (error.response?.data?.errors) {
        const errors = error.response.data.errors;
        setError(Object.values(errors).join(', '));
      } else {
        setError('Failed to add credit card');
      }
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    addCardMutation.mutate(formData);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <Card sx={{ mb: 4 }}>
      <CardContent>
        <Typography variant="h5" component="h2" gutterBottom>
          Add
        </Typography>
        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}
        <Box component="form" onSubmit={handleSubmit} noValidate>
          <TextField
            fullWidth
            label="Name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            margin="normal"
            required
            error={!!error}
          />
          <TextField
            fullWidth
            label="Card Number"
            name="cardNumber"
            value={formData.cardNumber}
            onChange={handleChange}
            margin="normal"
            required
            error={!!error}
            inputProps={{ maxLength: 19 }}
          />
          <TextField
            fullWidth
            label="Limit"
            name="limit"
            value={formData.limit}
            onChange={handleChange}
            margin="normal"
            required
            error={!!error}
            type="number"
            InputProps={{
              startAdornment: <span>£</span>,
            }}
          />
          <Button
            type="submit"
            variant="contained"
            color="primary"
            //fullWidth
            sx={{ mt: 2 }}
            disabled={addCardMutation.isPending}
          >
            {addCardMutation.isPending ? 'Adding...' : 'Add'}
          </Button>
        </Box>
      </CardContent>
    </Card>
  );
};

export default CreditCardForm; 