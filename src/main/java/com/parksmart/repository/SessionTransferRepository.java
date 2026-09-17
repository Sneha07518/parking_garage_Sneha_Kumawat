package com.parksmart.repository;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository; import com.parksmart.entity.SessionTransfer;
public interface SessionTransferRepository extends JpaRepository<SessionTransfer,Long>{List<SessionTransfer> findBySessionIdOrderByTransferredAtAsc(Long sessionId);}