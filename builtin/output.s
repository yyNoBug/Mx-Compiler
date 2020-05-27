	.section	.data
global0:
	.zero	4
global1:
	.zero	4
global2:
	.zero	4
	.section	.rodata
	.section	text
	.globl	main
main:
.main:
	addi	sp,sp,-144
	sw	ra,140(sp)
.init_global:
	li	t3,50
	sw	t3,136(sp)
	lw	t3,136(sp)
	mv	a0,t3
	call	__mallocArray__
	mv	t5,a0
	sw	t5,132(sp)
	lw	t3,132(sp)
	sw	t3,global0,t5
	sw	t5,128(sp)
	j	.main.entry
.main.entry:
	call	__getInt__
	mv	t5,a0
	sw	t5,124(sp)
	lw	t3,124(sp)
	sw	t3,global1,t5
	sw	t5,120(sp)
	li	t3,0
	sw	t3,116(sp)
	lw	t3,116(sp)
	sw	t3,global2,t5
	sw	t5,112(sp)
	j	.for.0.condition
.for.0.condition:
	lw	t3,global2
	sw	t3,108(sp)
	lw	t3,global1
	sw	t3,104(sp)
	lw	t3,108(sp)
	lw	t4,104(sp)
	sub	t5,t3,t4
	sw	t5,100(sp)
	lw	t3,100(sp)
	sltz	t5,t3
	sw	t5,96(sp)
	lw	t3,96(sp)
	beqz	t3,.for.0.end
	j	.for.0.body
.for.0.body:
	lw	t3,global0
	sw	t3,92(sp)
	lw	t3,global2
	sw	t3,88(sp)
	li	t3,4
	sw	t3,84(sp)
	lw	t3,88(sp)
	lw	t4,84(sp)
	mul	t5,t3,t4
	sw	t5,80(sp)
	lw	t3,92(sp)
	lw	t4,80(sp)
	add	t5,t3,t4
	sw	t5,76(sp)
	call	__getInt__
	mv	t5,a0
	sw	t5,72(sp)
	lw	t4,76(sp)
	lw	t3,72(sp)
	sw	t3,0(t4)
	lw	t3,global2
	sw	t3,68(sp)
	li	t3,0
	sw	t3,64(sp)
	lw	t3,68(sp)
	lw	t4,64(sp)
	sub	t5,t3,t4
	sw	t5,60(sp)
	lw	t3,60(sp)
	snez	t5,t3
	sw	t5,56(sp)
	lw	t3,56(sp)
	beqz	t3,.if.0.end
	j	.if.0.then
.if.0.then:
	lw	t3,global0
	sw	t3,52(sp)
	lw	t3,global2
	sw	t3,48(sp)
	li	t3,1
	sw	t3,44(sp)
	lw	t3,48(sp)
	lw	t4,44(sp)
	sub	t5,t3,t4
	sw	t5,40(sp)
	li	t3,4
	sw	t3,36(sp)
	lw	t3,40(sp)
	lw	t4,36(sp)
	mul	t5,t3,t4
	sw	t5,32(sp)
	lw	t3,52(sp)
	lw	t4,32(sp)
	add	t5,t3,t4
	sw	t5,28(sp)
	lw	t4,28(sp)
	lw	t5,0(t4)
	sw	t5,24(sp)
	lw	t3,24(sp)
	mv	a0,t3
	call	__printInt__
	mv	t5,a0
	sw	t5,20(sp)
	j	.if.0.end
.if.0.else:
	j	.if.0.end
.if.0.end:
	j	.for.0.step
.for.0.step:
	lw	t3,global2
	sw	t3,16(sp)
	li	t3,1
	sw	t3,12(sp)
	lw	t3,16(sp)
	lw	t4,12(sp)
	add	t5,t3,t4
	sw	t5,8(sp)
	lw	t3,8(sp)
	sw	t3,global2,t5
	sw	t5,4(sp)
	j	.for.0.condition
.for.0.end:
	li	t3,0
	sw	t3,0(sp)
	lw	t3,0(sp)
	mv	a0,t3
	j	.main.exit
.main.exit:
	lw	ra,140(sp)
	addi	sp,sp,144
	jr	ra