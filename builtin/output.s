	.section	.data
	.section	.rodata
	.section	text
	.globl	main
deal:
.deal:
	addi	sp,sp,-48
	sw	ra,44(sp)
.deal.entry:
	li	t3,40
	sw	t3,36(sp)
	lw	t4,36(sp)
	add	t5,sp,t4
	sw	t5,32(sp)
	lw	t4,32(sp)
	sw	a0,0(t4)
	lw	t4,32(sp)
	lw	t5,0(t4)
	sw	t5,28(sp)
	li	t3,0
	sw	t3,24(sp)
	lw	t3,28(sp)
	lw	t4,24(sp)
	add	t5,t3,t4
	sw	t5,20(sp)
	li	t3,100
	sw	t3,16(sp)
	lw	t4,20(sp)
	lw	t3,16(sp)
	sw	t3,0(t4)
	li	t3,0
	sw	t3,12(sp)
	lw	t3,12(sp)
	mv	a0,t3
	j	.deal.exit
.deal.exit:
	lw	ra,44(sp)
	addi	sp,sp,48
	jr	ra
main:
.main:
	addi	sp,sp,-64
	sw	ra,60(sp)
.init_global:
	j	.main.entry
.main.entry:
	li	t3,56
	sw	t3,52(sp)
	lw	t4,52(sp)
	add	t5,sp,t4
	sw	t5,48(sp)
	li	t3,4
	sw	t3,44(sp)
	lw	t3,44(sp)
	mv	a0,t3
	call	__mallocObject__
	mv	t5,a0
	sw	t5,40(sp)
	lw	t4,48(sp)
	lw	t3,40(sp)
	sw	t3,0(t4)
	lw	t4,48(sp)
	lw	t5,0(t4)
	sw	t5,36(sp)
	lw	t3,36(sp)
	mv	a0,t3
	call	deal
	mv	t5,a0
	sw	t5,32(sp)
	lw	t4,48(sp)
	lw	t5,0(t4)
	sw	t5,28(sp)
	li	t3,0
	sw	t3,24(sp)
	lw	t3,28(sp)
	lw	t4,24(sp)
	add	t5,t3,t4
	sw	t5,20(sp)
	lw	t4,20(sp)
	lw	t5,0(t4)
	sw	t5,16(sp)
	lw	t3,16(sp)
	mv	a0,t3
	call	__printInt__
	mv	t5,a0
	sw	t5,12(sp)
	li	t3,0
	sw	t3,8(sp)
	lw	t3,8(sp)
	mv	a0,t3
	j	.main.exit
.main.exit:
	lw	ra,60(sp)
	addi	sp,sp,64
	jr	ra