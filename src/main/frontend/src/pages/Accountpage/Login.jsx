// 로그인 폼 (MUI + React)
import React from 'react';
import { TextField, Button, Checkbox, FormControlLabel, Typography, Box, Paper } from '@mui/material';

export function Login() {
    return (
        <Box display="flex" height="100vh">
            <Box flex={1} display="flex" justifyContent="center" alignItems="center">
                <Paper elevation={3} sx={{ padding: 4, width: 350 }}>
                    <img src="/logo192.png" alt="logo" style={{ width: 100, margin: '0 auto', display: 'block' }} />
                    <Typography textAlign="center" mt={2}>로그인을 하셔야 서비스를 이용하실 수 있습니다.</Typography>
                    <TextField fullWidth label="아이디를 입력해 주세요" margin="normal" />
                    <TextField fullWidth label="비밀번호를 입력해 주세요" type="password" margin="normal" />
                    <Button variant="contained" color="primary" fullWidth>LOGIN</Button>
                </Paper>
            </Box>
            <Box flex={1} sx={{ background: 'linear-gradient(to bottom right, #8cd3aa, #6ab7a8)' }} />
        </Box>
    );
}