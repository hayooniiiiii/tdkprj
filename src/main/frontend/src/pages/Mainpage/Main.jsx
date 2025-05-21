import React, { useEffect, useState } from 'react';
import {
    Box,
    Card,
    CardContent,
    Avatar,
    Typography,
    Button,
    Tabs,
    Tab,
    Divider, ListItemText, ListItem,List
} from '@mui/material';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

export function Main() {
    const [tab, setTab] = useState(1);
    const [profile, setProfile] = useState(null);
    const [buseoName, setBuseoName]= useState(null);
    const navigate = useNavigate();
    const [myDocuments, setMyDocuments] = useState([]);

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const res = await axios.get('http://localhost:8080/main/profile', {
                    withCredentials: true,
                });

                // 응답 구조가 { user: {...}, buseoName: "..." } 형태임
                setProfile(res.data.user);
                setBuseoName(res.data.buseoName); // 별도 상태 저장
            } catch (error) {
                console.error('프로필 가져오기 실패:', error);
            }
        };
        const fetchMyDocuments = async () => {
            try {
                const res = await axios.get('http://localhost:8080/document/my', {
                    withCredentials: true,
                });
                setMyDocuments(res.data); // DocumentDto 리스트로 예상
            } catch (error) {
                console.error('내 문서 목록 불러오기 실패:', error);
            }
        };
        fetchProfile();
        fetchMyDocuments();
    }, []);


    const handleTabChange = (event, newValue) => {
        setTab(newValue);
    };

    // 입사일 기준 경과 연/월 계산
    const getWorkingPeriod = (ipsaDateStr) => {
        if (!ipsaDateStr) return '';
        const ipsaDate = new Date(ipsaDateStr);
        const now = new Date();
        let years = now.getFullYear() - ipsaDate.getFullYear();
        let months = now.getMonth() - ipsaDate.getMonth();

        if (months < 0) {
            years -= 1;
            months += 12;
        }

        return `${years}년 ${months}개월`;
    };

    return (
        <Box display="flex" p={4} bgcolor="#f5f5f5" minHeight="100vh" gap={4}>
            {/* 좌측 프로필 카드 */}
            <Card sx={{ width: 300, bgcolor: '#009688', color: 'white', borderRadius: 3 }}>
                <CardContent>
                    <Box display="flex" justifyContent="center" mb={2}>
                        <Avatar
                            src="https://via.placeholder.com/100"
                            alt="프로필"
                            sx={{ width: 80, height: 80 }}
                        />
                    </Box>
                    <Typography align="center" variant="h6">
                        {profile ? profile.userName : '로딩 중...'}
                    </Typography>
                    <Typography align="center" variant="body2">
                        {buseoName ? buseoName : '...'}
                    </Typography>

                    <Box mt={3} display="flex" justifyContent="center">
                        <Button variant="outlined" sx={{ color: 'white', borderColor: 'white' }}>
                            마이페이지
                        </Button>
                    </Box>
                    <Box mt={1} display="flex" justifyContent="center">
                        <Button
                            variant="outlined"
                            sx={{
                                color: 'white',
                                borderColor: 'white',
                                mt: 1,
                                width: '100%',
                                '&:hover': {
                                    borderColor: '#ffffffcc',
                                    backgroundColor: '#00796b'
                                }
                            }}
                            onClick={() => {
                                alert('계출서 작성 페이지로 이동합니다.');
                                navigate("/input")
                            }}
                        >
                            계출서 작성
                        </Button>
                    </Box>

                    <Divider sx={{ backgroundColor: 'rgba(255,255,255,0.3)', my: 3 }} />

                    <Divider sx={{ backgroundColor: 'rgba(255,255,255,0.3)', my: 3 }} />

                    <Typography variant="body2">
                        {profile ? `TDK와 함께 ${getWorkingPeriod(profile.userIpsa)}` : ''}
                    </Typography>

                    <Box display="flex" justifyContent="space-between" mt={2}>
                        <Box>
                            <Typography variant="caption">미결재현황</Typography>
                            <Typography variant="h6">0</Typography>
                        </Box>
                        <Box>
                            <Typography variant="caption">서약관리현황</Typography>
                            <Typography variant="h6">0</Typography>
                        </Box>
                    </Box>
                </CardContent>
            </Card>

            {/* 우측 카드 - 보낸함/받은함 탭 */}
            <Box flex={1}>
                <Card sx={{ borderRadius: 3 }}>
                    <CardContent>
                        <Tabs value={tab} onChange={handleTabChange} indicatorColor="primary" textColor="primary">
                            <Tab label="받은함" value={0} />
                            <Tab label="보낸함" value={1} />
                        </Tabs>

                        <Divider sx={{ my: 2 }} />

                        {tab === 1 ? (
                            <Box>
                                <List>
                                    {myDocuments.length > 0 ? (
                                        myDocuments.map((doc) => (
                                            <ListItem button key={doc.id} divider onClick={() => navigate(`/document/${doc.id}`)}>
                                                <ListItemText
                                                    primary={doc.documentTitle}
                                                    secondary={`작성일: ${doc.documentDate} / 공개여부: ${doc.documentOpen === '1' ? '공개' : '비공개'}`}
                                                />
                                            </ListItem>
                                        ))
                                    ) : (
                                        <Typography variant="body2" color="text.secondary">
                                            작성한 문서가 없습니다.
                                        </Typography>
                                    )}
                                </List>
                            </Box>
                        ) : (
                            <Typography variant="body2" color="text.secondary">
                                받은함 내용이 없습니다.
                            </Typography>
                        )}
                    </CardContent>
                </Card>
            </Box>
        </Box>
    );
}
