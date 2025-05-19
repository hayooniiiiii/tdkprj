import React, { useState } from 'react';
import {
    TextField,
    Button,
    Typography,
    Box,
    Paper
} from '@mui/material';
import axios from 'axios';

export function Login() {
    const [userLoginid, setUserLoginid] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        try {
            const response = await axios.post(
                'http://localhost:8080/login/user',
                {
                    userLoginid: userLoginid,
                    userPassword: password
                },
                {
                    withCredentials: true // ✅ 쿠키 포함 필수!
                }
            );

            alert('로그인 성공!');
            // 쿠키는 자동 저장됨, 따로 저장할 필요 없음
            // 이후 페이지 이동 등 처리
        } catch (error) {
            console.error(error);
            alert('로그인 실패');
        }
    };

    return (
        <Box display="flex" height="100vh">
            <Box flex={1} display="flex" justifyContent="center" alignItems="center">
                <Paper elevation={3} sx={{ padding: 4, width: 350 }}>
                    <img
                        src="/logo192.png"
                        alt="logo"
                        style={{ width: 100, margin: '0 auto', display: 'block' }}
                    />
                    <Typography textAlign="center" mt={2}>
                        로그인을 하셔야 서비스를 이용하실 수 있습니다.
                    </Typography>
                    <TextField
                        fullWidth
                        label="아이디를 입력해 주세요"
                        margin="normal"
                        value={userLoginid}
                        onChange={(e) => setUserLoginid(e.target.value)}
                    />
                    <TextField
                        fullWidth
                        label="비밀번호를 입력해 주세요"
                        type="password"
                        margin="normal"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <Button
                        variant="contained"
                        color="primary"
                        fullWidth
                        onClick={handleLogin}
                    >
                        LOGIN
                    </Button>
                </Paper>
            </Box>
            <Box
                flex={1}
                sx={{
                    background: 'linear-gradient(to bottom right, #8cd3aa, #6ab7a8)'
                }}
            />
        </Box>
    );
}
